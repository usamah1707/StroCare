package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.receiver.AlarmReceiver
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.RandomIntUtil
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setRepetitiveAlarm(timeinMillis: Long) {
        setAlarm(
            timeinMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_REPETITIVE_ALARM
                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeinMillis)
                }
            )
        )
    }

    private fun setAlarm(timeinMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeinMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeinMillis,
                    pendingIntent
                )
            }
        }
    }

    fun deleteAlarm() {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
        }

        alarmManager?.cancel(getPendingIntent(
            getIntent().apply {
                action = Constants.ACTION_SET_REPETITIVE_ALARM
                putExtra(Constants.EXTRA_EXACT_ALARM_TIME, cal.timeInMillis)
            }
        ))
    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent(intent: Intent): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            RandomIntUtil.getRandomInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
}