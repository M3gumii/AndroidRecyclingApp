import com.example.recyclingapp.dataClasses.ai.dataClasses.AIChoice
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Represents the response returned by the AI.
 *
 * Contains the request ID,
 * the type of object returned,
 * and the list of generated choices from the model.
 */
@JsonClass(generateAdapter = true)
data class AIResponse(
    val id: String?,
    @Json(name = "object") val type: String?,
    val choices: List<AIChoice> //Each choice = 1 generated response from the AI!
)