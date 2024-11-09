package ecommerce.project.manlywear.Utils

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object EventDispatcher {
    private var _networkStatus = Channel<String>()
    val networkStatus = _networkStatus.receiveAsFlow()

    suspend fun sendNetworkStatus(result: String){
        _networkStatus.send(result)
    }
}