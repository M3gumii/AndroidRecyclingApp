# AndroidRecyclingApp
Recycling app utilizing Kotlin and Java in Android Studio.
----------------------------------------------------------------------

A simple recycling app used to help the Columbus, OH population with
recycling their food packaging!

Utilizes a Supabase DB as well as Camera MLKit for package barcode 
scanning! Coil used for image display.

Copilot and the Open Food Facts website used for new item data 
fetching via barcodes!

## 🔑 API Key Setup

This project requires a Google AI Studio API key.

1. Create a file named `local.properties` in the project root.
2. Add the following line:

   AI_API_KEY=your_key_here

3. Build and run the project.

Note: `local.properties` is intentionally excluded from version control.

----------------------------------------------------------------------


SUPABASE DB CONNECTION
----------------------------------------------------------------------

**Supabase (REST API)**  

↓  
**Retrofit** – Defines API 

↓  
**OkHttpClient** – Sends requests, attaches headers, handles networking  

↓  
**Moshi** – Converts JSON responses into Kotlin objects  

↓  
**Kotlin Data Classes** – Ready to use inside the app, 1 for each table!

<img width="824" height="552" alt="image" src="https://github.com/user-attachments/assets/78a58bce-8801-48df-93fe-153755080b59" />


Previous Searches only keeps the latest 10 inserts by a user via Supabase Postgres triggers.

----------------------------------------------------------------------


IMAGE SOURCES
----------------------------------------------------------------------

<a href="https://www.flaticon.com/free-icons/recycling" title="recycling icons">Recycling icons created by juicy_fish - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/account" title="account icons">Account icons created by graphicmall - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/housing" title="housing icons">Housing icons created by Freepik - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/camera" title="camera icons">Camera icons created by Freepik - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/loading-bar" title="loading bar icons">Loading bar icons created by kerismaker - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/recent" title="recent icons">Recent icons created by Karacis - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/search" title="search icons">Search icons created by Freepik - Flaticon</a>

----------------------------------------------------------------------
