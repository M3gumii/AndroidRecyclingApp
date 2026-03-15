package com.example.recyclingapp.dataClasses.ai

import android.util.Log
import com.example.recyclingapp.dataClasses.ai.dataClasses.AIContent
import com.example.recyclingapp.dataClasses.ai.dataClasses.AIPart
import com.example.recyclingapp.dataClasses.ai.dataClasses.AIRequest
import org.json.JSONObject

/**
 * Sends a barcode prompt to the AI and returns the JSON result.
 *
 * Builds a request using the barcode, sends it through the AI API,
 * and converts the AI's response into a JSONObject.
 */
class AIPrompter {

    /**
     * Prompts Gemini to get data based on the barcode sent.
     *
     * AIRetrofitApi created from AIContactor!
     */
    suspend fun lookupBarcode(barcode: String, api: AIRetrofitApi): JSONObject {
        val prompt = """
        You are a barcode lookup engine.

        You will be given a barcode. If you recognize the barcode and can reliably
        identify a real product, return its data.

        When you DO know the product, respond with a single JSON object:

        {
          "barcode": "...",
          "name": "...",
          "recycling_pos": true/false,
          "description": "..."
        }

        Rules:
        - Respond ONLY with valid JSON.
        - Never include explanations or text outside the JSON.
        - Never return an array, only a single JSON object.

        Input barcode: $barcode
    """.trimIndent()

        val request = AIRequest(
            contents = listOf(
                AIContent(
                    parts = listOf(
                        AIPart(prompt)
                    )
                )
            )
        )

        Log.d("AI PROMPTER", "PROMPT:\n$prompt")

        val response = api.askAI(request)

        // ⭐ FIXED: Gemini response extraction
        val text = response
            .candidates.first()
            .content.parts.first()
            .text

        Log.d("AI PROMPTER", "RAW TEXT FROM GEMINI: $text")

        return try {
            JSONObject(text)
        } catch (e: Exception) {
            Log.e("AIPrompter", "Invalid JSON from AI: $text")
            JSONObject()
        }
    }
}