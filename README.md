# AndroidRecyclingApp
Recycling app utilizing Kotlin and Java in Android Studio.
----------------------------------------------------------------------

A simple recycling app used to help the Columbus, OH population with
recycling their food packaging!

Utilizes a Supabase DB as well as Camera MLKit for package barcode 
scanning!

----------------------------------------------------------------------


SUPABASE DB CONNECTION
----------------------------------------------------------------------

Supabase (REST API) 
      ↓
Retrofit (API interface, defines endpoints) 
      ↓
OkHttpClient (sends request, adds headers, handles network) 
      ↓
Moshi (converts JSON → Kotlin objects) 
      ↓
Kotlin (your data classes, ready to use in app)

----------------------------------------------------------------------


IMAGE SOURCES
----------------------------------------------------------------------

<a href="https://www.flaticon.com/free-icons/recycling" title="recycling icons">Recycling icons created by juicy_fish - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/account" title="account icons">Account icons created by graphicmall - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/housing" title="housing icons">Housing icons created by Freepik - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/camera" title="camera icons">Camera icons created by Freepik - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/recent" title="recent icons">Recent icons created by Karacis - Flaticon</a>

<a href="https://www.flaticon.com/free-icons/search" title="search icons">Search icons created by Freepik - Flaticon</a>

----------------------------------------------------------------------
