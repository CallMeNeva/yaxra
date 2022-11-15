:warning: **This project is currently WIP**

## Usage
* `GET /currencies` returns all supported currencies. Example:
  ```json
  {
    "376": "Israeli New Shekel",
    "840": "United States Dollar",
    "008": "Albanian Lek",
    "978": "Euro",
    "060": "Bermudian Dollar"
  }
  ```
  If there's no data, an empty JSON object is returned. A `200 OK` is returned on both empty and non-empty responses.

* `GET /currencies/{n}` returns the currency identified by the specified ISO 4217 numeric currency code `{n}`. 
  Example: `GET /currencies/840`
  ```json
  {
    "alphaCode": "USD",
    "name": "United States Dollar"
  }
  ```
  Status codes:
  * `200 OK` on successful identification
  * `404 Not Found` on unsuccessful identification
  * `400 Bad Request` if the given ID is not a 3-digit number as per ISO 4217

* `POST /currencies` creates a new currency. Example:
  ```json
  {
    "numCode": 376,
    "alphaCode": "ILS",
    "name": "Israeli New Shekel"
  }
  ```
  Status codes:
  * `201 Created` if the provided entity is valid and was successfully saved
  * `422 Unprocessable Entity` if the provided entity is not valid and therefore cannot be saved

* `GET /rates/{n}/{date}` returns exchange rates. `{n}` must be a valid base currency numeric code, `{date}` must be 
  formatted as per ISO 8601. Example: `GET /rates/840/2000-01-01`
  ```json
  {
    "376": 3.4308,
    "978": 0.96117
  }
  ```
  Status codes:
  * `400 Bad Request` if either of the base currency ID or formatted date is invalid (see other endpoints' docs)
  * `200 OK` on successful fetch (lack of data in response to a well-formed request returns an empty JSON object and 
    `200 OK` as well)

## Setup
*Coming soon™*
