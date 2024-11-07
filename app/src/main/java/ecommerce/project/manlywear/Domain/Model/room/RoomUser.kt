package ecommerce.project.manlywear.Domain.Model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class RoomUser(
    @PrimaryKey(autoGenerate = false)
    var username : String,
    @ColumnInfo(name = "password")
    var password : String
)
