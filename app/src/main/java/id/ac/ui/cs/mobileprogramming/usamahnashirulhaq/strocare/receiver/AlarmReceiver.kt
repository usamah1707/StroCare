package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
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
                buildNotification(context, "StroCare Reminder Repetitive", "Jangan lupa minum obat")
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