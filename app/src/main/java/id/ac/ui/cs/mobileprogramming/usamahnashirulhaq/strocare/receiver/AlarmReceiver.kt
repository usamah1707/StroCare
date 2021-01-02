package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.service.AlarmService
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants
import io.karn.notify.Notify
import java.util.*
import java.util.concurrent.TimeUnit


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Constants.ACTION_SET_REPETITIVE_ALARM -> {
                setRepetitiveAlarm(AlarmService(context))
                buildNotification(
                    context, context.getString(R.string.judul_notifikasi), context.getString(
                        R.string.pesan_notifikasi
                    )
                )
            }
        }
    }

    private fun buildNotification(context: Context, title: String, message: String) {
        Notify
            .with(context)
            .content {
                this.title = title
                this.text = "Reminder: $message"
            }
            .show()
    }

    private fun setRepetitiveAlarm(alarmService: AlarmService) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
        }
        alarmService.setRepetitiveAlarm(cal.timeInMillis)
    }
}