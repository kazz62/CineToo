package com.kazz.cinetoo.domain.model

/**
 * Predefined list of streaming platforms matching TMDb watch provider IDs
 */
object PlatformData {
    val availablePlatforms = listOf(
        StreamingPlatform(id = 8, name = "Netflix", logoPath = "/pbpMk2JmcoNnQwx5JGpXngfoWtp.jpg"),
        StreamingPlatform(id = 337, name = "Disney +", logoPath = "/7rwgEs15tFwyR9NPQ5vpzxTj19Q.jpg"),
        StreamingPlatform(id = 9, name = "Prime Video", logoPath = "/emthp39XA2YScoYL1p0sdbAH2WA.jpg"),
        StreamingPlatform(id = 384, name = "HBO Max", logoPath = "/Ajqyt5aNxNGjmF9uOfxArGrdf3X.jpg"),
        StreamingPlatform(id = 350, name = "Apple TV+", logoPath = "/6uhKBfmtzFqOcLousHwZuzcrScK.jpg"),
        StreamingPlatform(id = 531, name = "Paramount +", logoPath = "/xbhHHa1YgtpwhC8lb1NQ3ACVcLd.jpg")
    )
}
