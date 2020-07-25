# BookStore Application  <img src="https://user-images.githubusercontent.com/50637297/83944429-1486ff00-a821-11ea-8aea-0714d852214d.png" alt="BookStore LOGO" width="100" height="40">

## ![BridgeLabz](https://bridgelabz.com/) <img src="https://user-images.githubusercontent.com/50637297/83942283-d1706000-a80f-11ea-99fb-6c26326a7602.png" alt="BLZ LOGO" width="100" height="50">


## Abstract:

The **business-to-customer** aspect of **electronic commerce (_e-commerce_)** is the most visible business use of the ***World Wide Web***. The primary goal of an e-commerce site is to ***sell books*** and ***services online*** and the project deals with developing an e-commerce website for **Online Book Store**. It provides the user with a catalog of different books available for purchase in the store. In order to facilitate online purchase a shopping cart is provided to the user.


### Overview of Online Book Store Application:

- Online Book Store is an online web application where the _customer can purchase books online_. Through a web browser the customers can _search for a book_ by it's **title** or **author** or **cost**, later **can add to the shopping cart** and finally **purchase the books**. After purchasing the book, users can **give ratings** for that book.

- The online book store application enables **vendors** _to set up online book stores_, **customers** to _browse through the books_, and a **system administrator** to _approve and reject requests for new books and maintain lists of book categories_.


### Objectives:

- The main objective of **"Online Book Store"** is to provide an essence of online book store via a simple and yet powerful medium.

- The project has been designed to simulate the working of an actual online book store.


### Functional Requirements:

- **Browse Books:** An user logging into the site can browse through the boks both by category or in a random manner.

- **View Details:** An user can also view the details of the books(eg:price, athor, title).

- **Add to cart:** The user can add the books of his choice to the shopping cart.

- **Make payments:** The user ultimately visits his cart and makes the payments

- **Review for Books:** Users can give reviews for books.


### Risk Analysis:

If an item is not in stock, then we provide information that is **"Out of Stock"**, so members can add that item in the recommended item.


### Advantages:
- Cost saving
- Time saving
- Fast purchasing
- View books of all categories


### Tools to be used:

#### Frontend Tools:

```
- Angular - Version ()
- ReactJS - Version ()
```

#### Backend Tools:
```
- Java - Version (1.8)
```

#### Database:
```
- Mysql - version ()
```


### Modules:

- Home
- Admin
- Vendor
- User
- Shopping Cart

#### 1. Home:
 - Search
 - Most popular Subjects
 - Top Authors
 - Top New Releases
 - Complete Collections
 - Customer Reviews

#### 2. Admin:
  - Login Form
  - Product management

#### 3. Vendor
  - Login Form
  - Manage Book
  - Manage Quantity
  - Manage price

#### 4. User:
  - Login FormBooks in my wish list
  - Items in my shopping cart
  - My Order

#### 5. Shipping Cart
  - Number of items
  - List of books with price
  - Total Amount
  - Address
  - Remove from cart
  - Payments


### Data Flow Diagram(DFD)

#### Context Level DFD(DFD- Level 0)

![](https://user-images.githubusercontent.com/50637297/83981249-d6313300-a939-11ea-9de2-819b041d532b.jpg)


### ENV variables:

```
profile=development

emailId=mail
emailPass=password

rabbitPass=guest
rabbitUser=guest
exchange=obs-exchange
queue=obs-queue
routingkey=obs-routingkey

port=3306
dbName=dbName
dbPass=root
dbUser=root


awsurl=url
accesskey=accessKey
secretkey=secretKey
bookBucketName=bookstorebook
bucketname=onlinebookstoreapp
region=region
password=password
key=key

forgot-password-template-path=TemplatePath
login-template-path=TemplatePath
registration-template-path=TemplatePath
book-approval-template-path=TemplatePath
book-deletion-template-path=TemplatePath
book-rejection-template-path=TemplatePath
```

### How to solve redis error:

```
redis-server
redis-cli ping
redis-cli
CONFIG SET requirepass password
AUTH password 
```
### How to solve Elasticsearch error:

```
sudo systemctl start elasticsearch.service
sudo systemctl stop elasticsearch.service


drop table ReviewApp, address, book, cart, cart_book, hibernate_sequence, myorderlist, orders, orders_book, review,  wishlist, wishlist_has_books;



```
#### DFD for USER(DFD- Level 1)

![](https://user-images.githubusercontent.com/50637297/83981250-d7faf680-a939-11ea-8949-0e0003925830.jpg)


### Conclusion:
Online book store is an online web application where the customer can purchase books online. Through a web browser the customers can search for a book by it's title or author or cost, later can add to the shopping cart and finally purchase the books. After purchasing the book, users can give ratings for that book.


# Update
