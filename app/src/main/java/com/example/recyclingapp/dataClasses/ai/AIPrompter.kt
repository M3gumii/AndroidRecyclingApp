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
    suspend fun lookupItem(name: String?, barcode: String, api: AIRetrofitApi): JSONObject {
        val prompt = """
        You are a packaging inference engine.
        
        You will be given:
        - A product name
        
        Your job is to infer the packaging material and recyclability based ONLY on the 
        product name and Columbus, OH recycling rules.
        
        Return ONLY a single JSON object in this exact structure:
        
        {
          "barcode": $barcode,
          "name": $name,
          "recycling_pos": true/false, <- decide based upon Columbus, OH recycling rules.
          "image_link": null,
          "description": "A simple explanation of the packaging and how to recycle it.",
          "verified": false
        }
        
        RULES:
        - Respond ONLY with raw JSON.
        - Do NOT wrap the JSON in code fences or markdown.
        - Do NOT include explanations or reasoning.
        - Do NOT return an array.
        - Do NOT change or guess the product name.
        - If packaging information is missing/you cannot tell how to recycle the item, 
            set the barcode field to NULL!
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
        var text = response
            .candidates.first()
            .content.parts.first()
            .text

        text = text.removePrefix("```json").removePrefix("```")
            .removeSuffix("```").trim() //Get the final valid obj!


        Log.d("AI PROMPTER", "RAW TEXT FROM GEMINI: $text")

        return try {
            JSONObject(text)
        } catch (e: Exception) {
            Log.e("AIPrompter", "Invalid JSON from AI: $text")
            JSONObject()
        }
    }
}