package me.dy.tmstester.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.tms.sdk.TMS

/**
 * Created by daeyoon on 2017. 12. 13..
 */
class CustomNotiReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Push notiMsg : " + intent.getStringExtra(TMS.KEY_NOTI_MSG),
                Toast.LENGTH_SHORT).show()
    }

}