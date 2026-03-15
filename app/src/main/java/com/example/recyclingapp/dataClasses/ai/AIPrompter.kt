package com.example.recyclingapp.dataClasses.ai

import com.example.recyclingapp.dataClasses.ai.dataClasses.AIMessage
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
                You will be given a barcode, via the barcode, find the 
                food packaging's name, and what the packaging is made of.
                Based on what the packaging is mostly made of (like a cardboard box) 
                set recycling_pos to true or false based on Columbus, Ohio's recycling
                rules.
                Then fill in the description field with simple details on how to recycle the item.
                Input: $barcode
                Output: JSON with fields:
                {
                  "barcode": "...",
                  "name": "...",
                  "recycling_pos": true/false,
                  "description": "..."
                }
                Respond ONLY with valid JSON.
                An example output would be:
                [{"barcode":"00011110080005",
                "name":"Kroger Purified Water 16.9 oz Bottle",
                "recycling_pos":true,
                "description":"Clear PET water bottle recyclable in Columbus; cap recyclable."}]
                """.trimIndent()


        val request = AIRequest(
            messages = listOf(AIMessage("user", prompt))
        )

        val response = api.askAI(request)
        val json = response.choices.first().message.content
        return JSONObject(json)
    }
}