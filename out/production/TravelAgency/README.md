**Travel Agency Schema**
Tables
Users
FirstName, LastName, Username, Password, (CreditCardNumber), isAdmin, isActive
password should have a trigger constraint for > 4 chars
Package
ID, Hotel, Flight, Location, Price
Booking
ID, User, Package, StartDate, EndDate
Hotel
ID, Name, Location, Cost
Airline
Name
Flight
ID, OriginCity, DestinationCity, Airline, Cost
City
cityName
Transactions
ID, Booking, CreditCardNumber, Amount
AmountPaid → (pay in installments, payment doesn’t need to be full booking amount)
