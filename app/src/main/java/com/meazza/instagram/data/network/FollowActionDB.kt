package com.meazza.instagram.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.meazza.instagram.data.model.User
import com.meazza.instagram.util.*
import kotlinx.coroutines.tasks.await

object FollowActionDB {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val currentUserUid by lazy { FirebaseAuth.getInstance().currentUser?.uid!! }

    private val followRef = db.collection(FOLLOW_REF)
    private val usersRef = db.collection(USER_REF)

    suspend fun checkIfCurrentUserIsFollowing(instagrammerId: String): DocumentSnapshot? =
        followRef.document(currentUserUid).collection(FOLLOWING).document(instagrammerId)
            .get().await()

    suspend fun saveFollow(instagrammer: User) {

        val instagramUserDocRef = usersRef.document(instagrammer.id!!)
        val currentUserDocRef = usersRef.document(currentUserUid)

        followRef.document(currentUserUid)
            .collection(FOLLOWING).document(instagrammer.id).set(
                hashMapOf(
                    UID to instagramUserDocRef
                )
            ).addOnSuccessListener { updateFollowing() }.await()

        followRef.document(instagrammer.id).collection(FOLLOWER).document(currentUserUid).set(
            hashMapOf(
                UID to currentUserDocRef
            )
        ).addOnSuccessListener { updateFollower(instagrammer.id) }.await()
    }

    suspend fun stopFollowing(instagrammer: User) {
        followRef.document(currentUserUid)
            .collection(FOLLOWING).document(instagrammer.id!!).delete()
            .addOnSuccessListener {
                updateFollowing()
            }.await()

        followRef.document(instagrammer.id)
            .collection(FOLLOWER).document(currentUserUid).delete()
            .addOnSuccessListener {
                updateFollower(instagrammer.id)
            }.await()
    }

    private fun updateFollowing() {
        followRef.document(currentUserUid).collection(FOLLOWING).get()
            .addOnSuccessListener {
                usersRef.document(currentUserUid).update(FOLLOWING_NUMBER, it.size())
            }
    }

    private fun updateFollower(uid: String) {
        followRef.document(uid).collection(FOLLOWER).get()
            .addOnSuccessListener {
                usersRef.document(uid).update(FOLLOWER_NUMBER, it.size())
            }
    }

    suspend fun getFollowersNumber(uid: String) =
        followRef.document(uid).collection(FOLLOWER).get().await().size()
}