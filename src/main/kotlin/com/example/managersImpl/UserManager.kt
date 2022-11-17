package com.example.managersImpl


import com.example.authorization.AuthUtil
import com.example.authorization.models.*
import com.example.database.models.*
import com.example.params.UserId
import com.example.managers.usersManager.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface UserManager {
    suspend fun login(loginModel: LoginModel): TokensModel

    suspend fun register(registerModel: RegisterUserModel): HttpStatusCode

    suspend fun getUser(id: UserId): GetUserModel
}

class UserManagerImpl : UserManager, KoinComponent {
    override suspend fun login(loginModel: LoginModel): TokensModel {
        val manager: LoginUserManager by inject()
        return runCatching {
            manager.invoke(Unit, loginModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            TokensModel(
                access = AuthUtil.buildAccess(it.userId.toString()),
                refresh = AuthUtil.buildRefresh(it.userId.toString()),
            )
        }
    }

    override suspend fun register(registerModel: RegisterUserModel): HttpStatusCode {
        val manager: RegisterUserManager by inject()
        return runCatching {
            manager.invoke(Unit, registerModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun getUser(id: UserId): GetUserModel {
        val manager: GetUserManager by inject()
        runCatching {
            println(id)
            manager.invoke(id, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            return it
        }
    }
}
