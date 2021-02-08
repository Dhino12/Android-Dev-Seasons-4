package com.example.reportapp

import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class ReleaseTree:Timber.Tree() {
    // Tetap menampilkan log ketika di aplikasi release
    // Optional
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if(t == null){
            FirebaseCrashlytics.getInstance().log(message)
        }else{
            FirebaseCrashlytics.getInstance().recordException(t)
        }
    }

}