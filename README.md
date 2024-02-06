
### Backend Tasks:

1. **Setup and Configuration:**
    
    - Set up a new Spring Boot project.
    - Configure database connection in `application.properties`.
    - Add necessary dependencies (Spring Web, Spring Data JPA, Spring Security, etc.) in your `pom.xml` file.
2. **User Management:**
    
    - Implement user registration.
    - Implement user authentication.
    - Implement password reset functionality.
    - Define user roles (Owner, Admin, Customer, Viewer).
3. **Property Management:**
    
    - Create models for `Property` and `Application`.
    - Implement CRUD operations for properties.
    - Implement the ability for owners to upload pictures of the property.
    - Integrate cloud services (Amazon S3, Google Cloud Storage) for storing property images.
4. **Search and Filter:**
    
    - Implement search and filter functionality based on price, property type, number of rooms, home type, and location.
5. **Applications:**
    
    - Allow customers to submit applications for properties.
    - Send email notifications to property owners when an application is submitted.
    - Implement the ability for owners to view and manage applications for their properties.
6. **Admin Dashboard:**
    
    - Implement an admin dashboard.
    - Display the last 10 properties rented.
    - Show 10 most recent customers.
    - Add any additional features you see fit.
7. **Activation/Deactivation:**
    
    - Allow admin to activate/deactivate customers and owners.
    - Implement the ability for owners to unlist properties.
8. **Security:**
    
    - Implement secure authentication mechanisms.
    - Ensure proper authorization based on user roles.
    - Implement secure communication with cloud services.
9. **Testing:**
    
    - Write unit tests for critical components.
    - Perform integration testing.
    - Ensure proper error handling and validation.
10. **Documentation:**
    
    - Document your code thoroughly.
    - Create API documentation using tools like Swagger.
11. **Deployment:**
    
    - Deploy the backend application to a server or cloud platform.

### Frontend Tasks:

1. **User Authentication:**
    
    - Create forms for user registration and login.
    - Implement password reset functionality with email notifications.
2. **Property Listing and Details:**
    
    - Display properties for viewers.
    - Allow owners to view and manage their properties.
    - Implement property details page with pictures.
3. **Search and Filter:**
    
    - Create a search bar and filters for customers to find properties.
4. **Applications:**
    
    - Allow customers to submit applications for properties.
    - Provide feedback to customers on the status of their applications.
5. **Admin Dashboard:**
    
    - Create a dashboard for admins displaying last rented properties, recent customers, and additional features as needed.
6. **Activation/Deactivation:**
    
    - Allow admins to manage users - activate or deactivate them.
7. **Favorites Lists:**
    
    - Allow customers to create and manage their favorite lists.
8. **Responsive Design:**
    
    - Ensure the portal is responsive for different devices.
9. **Testing:**
    
    - Perform frontend testing, including unit tests and integration tests.
    - Ensure proper error handling and validation.
10. **Documentation:**
    
    - Document your frontend code.
    - Provide user documentation for navigating and using the portal.
11. **Deployment:**
    
    - Deploy the frontend application to a server or cloud platform.




# Description of users and flow of system
### User Registration and Login:

1. **User Registration:**
    
    - Users register with the system, providing necessary details such as email, password, and role selection (Owner or Customer).
2. **User Authentication:**
    - Authenticated users log in using their credentials.

### Property Listing:

3. **Owner Creates Property Listing:**
    - Owners log in and access their dashboard.
    - Owners create property listings, providing details like address, description, and initial status (Available).

### Offer Placement:

4. **Customer Browses Listings:**
    
    - Customers log in and explore available property listings.
5. **Customer Places an Offer:**
    
    - Customers select a property and place an offer, specifying offer details and conditions.

### Offer Acceptance Process:

6. **Owner Reviews Offers:**
    
    - Owners receive notifications of new offers.
    - Owners log in to review and either accept or reject offers.
7. **Pending Phase:**
    
    - If the owner accepts an offer, the property status changes to Pending.
    - Both buyer and seller receive notifications to confirm their acceptance during this phase.

### Contingent Phase:

8. **Inspection and Payment Process:**
    - If both parties accept, the property status changes to Contingent.
    - Buyers may conduct property inspections.
    - Payment processes are initiated.

### Status Updates and Notifications:

9. **Status Updates:**
    - System updates property status based on inspections and payment verification.
    - Owners and buyers receive notifications for status changes.

### Offer History:

10. **View Offer History:**
    - Users can view their offer history, including details of past and current offers.

### Role Switching (Optional):

11. **Role Switching:**
    - Users with multiple roles can switch between Owner and Customer interfaces.

### Email Communication:

12. **Email Updates:**
    - Users receive detailed email updates for significant events, offer confirmations, and transaction-related information




## Entity 

 - Property
	 - id 
	 - title
	 - description
	 - propertyType ( enum )
	 - propertyStatus ( enum )
	 - listingDate
	 - address(Address)
	 - bedrooms
	 - totalArea
	 - lotSize
	 - amenities ( Amenities )
	 - price ( historical list)
	 - currency
	 - owner (User)
- Offer
	- offerid
	- propertyId
	- userId
	- offerAmount
	- offerStatus
	- offerDate
	- description
- Address
	- city 
	- state
	- zipCode
	- country
	- latitude
	- longitude
- User
	- id
	- name
	- email
	- userType
	- username
	- password
	- telephone
	- physicalAddress
	- 
- PropertyImage
	- url
	- propertyId
- 


## Endpoint
### property
api/v2/property api/va/property/id
api/v2/property/?located = 
api/v2/property/id/offers 
api/v2/updateproperty/id

### Users
api/v2/users/
api/v2/users/id/offers
api/v2/users/id/wishlist
api/v2/users/il/property


### Other
- api/v2/authenticate



## Work division
### backend
- abeni
- dani

### front end
- aman
- 


### devops
- abeni
- 

![[Pasted image 20240205152824.png]]
