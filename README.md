Menly Wear.
Menly Wear is a feature-rich Android e-commerce application designed for shopping men’s clothing and accessories. 
It allows users to browse products, add items to their Basket, and place orders securely. 
Once an order is placed, the app simulates order management processes using background services. 
The app leverages a variety of modern Android development tools and libraries to create a smooth and engaging user experience.



Features.
User Authentication: Firebase Authentication is integrated to provide secure sign-up and login functionality.
Product Browsing: Users can explore a wide range of men’s clothing and accessories. All products are retrieved from a remote server using Retrofit.
Basket & Order Management: Users can add products to their basket and place orders seamlessly.
Order Tracking: Simulated background tracking of order status using WorkManager.
Persistent Data Storage: Room database integration for storing cart items locally.
Modern UI/UX: Jetpack Compose enables a responsive, intuitive user interface with material design components.



Tech Stack.
Kotlin: Primary language for Android development.
Jetpack Compose: Used for creating a modern, declarative UI.
Hilt (Dagger): For dependency injection, allowing seamless integration of dependencies across the app.
Retrofit: For network calls to fetch product information from a remote API.
Room: For local data persistence, storing cart items and order data locally.
Firebase Authentication: For secure user authentication and registration.
WorkManager: To manage periodic tasks in the background, such as updating order status after placement.
Coroutines & Flows: For handling asynchronous tasks, like API calls and database operations, ensuring a smooth user experience.
Glide: For efficient image loading and caching of product images.



Architecture
Menly Wear follows the MVVM (Model-View-ViewModel) architecture pattern, with a clean separation of concerns:

Model: Responsible for data handling, using Retrofit for network calls and Room for local storage.
ViewModel: Manages UI-related data and exposes observable LiveData/StateFlows to the UI.
View: Composed using Jetpack Compose for declarative, reactive UI elements.



Summary.
Menly Wear is a comprehensive and modern shopping app, showcasing a solid grasp of Android development and design principles. 
It implements many best practices and leverages key tools in the Android ecosystem to deliver a high-quality user experience. 
This project is an excellent example of a full-featured app, demonstrating skills in UI/UX design, data persistence, background task management, and network communication.


Future Enhancements.
Push Notifications: Notify users when order status changes or for promotional offers.
Payment Gateway Integration: Integrate a real payment gateway for live transactions.
