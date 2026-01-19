package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.PlatformData
import com.kazz.cinetoo.domain.model.StreamingPlatform

class GetAvailablePlatformsUseCase {
    operator fun invoke(): List<StreamingPlatform> {
        return PlatformData.availablePlatforms
    }
}
