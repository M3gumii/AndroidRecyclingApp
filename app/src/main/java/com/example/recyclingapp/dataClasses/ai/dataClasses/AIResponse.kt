import com.example.recyclingapp.dataClasses.ai.dataClasses.AICandidate
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AIResponse(
    val candidates: List<AICandidate>
)
