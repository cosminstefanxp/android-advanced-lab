package ro.example.lab.data

import org.threeten.bp.Instant

data class Profile(val name: String?,
                   val profileImageUrl: String,
                   val createdAt: Instant = Instant.now())

data class Voucher(val expiresAt: Instant)
