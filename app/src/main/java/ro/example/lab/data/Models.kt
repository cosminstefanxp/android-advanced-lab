package ro.example.lab.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant

data class Profile(val name: String?,
                   val profileImageUrl: String,
                   val createdAt: Instant = Instant.now())

data class Voucher(val expiresAt: Instant)

@Entity
data class Note(@SerializedName("friendly_id")
                val userId: String,
                val message: String,
                @SerializedName("created")
                @PrimaryKey
                val createdAt: String)