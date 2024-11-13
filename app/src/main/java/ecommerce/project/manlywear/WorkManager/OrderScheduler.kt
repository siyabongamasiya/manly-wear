package ecommerce.project.manlywear.WorkManager

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import ecommerce.project.manlywear.Constants.ORDERID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OrderScheduler @Inject constructor(@ApplicationContext val context: Context) {

    fun scheduleTask(orderId: Int) {
        val inputData = Data.Builder()
            .putInt(ORDERID, orderId)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<OrderManager>(15, TimeUnit.MINUTES)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            ORDERID,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}