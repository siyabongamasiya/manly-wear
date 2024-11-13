package ecommerce.project.manlywear.WorkManager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ecommerce.project.manlywear.Constants.ORDERID
import ecommerce.project.manlywear.Domain.Repository.Repository
import javax.inject.Inject


@HiltWorker
class OrderManager @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val orderId = inputData.getInt(ORDERID, 0)
            val order = repository.getOrderById(orderId = orderId)

            if (order.status < 4) {
                order.status++
                repository.saveOrder(order)
            } else {
                repository.deleteOrderById(orderId)
            }

            Log.d("worker", "success")
            Result.success()

        } catch (e: Exception) {
            Log.d("worker", "fail")
            Result.retry()
        }
    }
}



class CustomWorkerFactory @Inject constructor(private val repository: Repository) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = OrderManager(appContext,workerParameters,repository)
}

