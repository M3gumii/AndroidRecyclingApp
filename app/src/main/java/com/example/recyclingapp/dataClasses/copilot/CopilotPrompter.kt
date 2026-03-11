package com.example.recyclingapp.dataClasses.copilot

import org.json.JSONObject

class CopilotPrompter {

    /**
     * Prompts Gemini to get data based on the barcode sent.
     */
    suspend fun lookupBarcode(barcode: String, api: CopilotRetrofitApi): JSONObject {
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


        val request = CopilotRequest(
            messages = listOf(CopilotMessage("user", prompt))
        )

        val response = api.askCopilot(request)
        val json = response.choices.first().message.content

        return JSONObject(json)
    }
}