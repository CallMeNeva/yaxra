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
* `GET /currencies/{n}` returns the currency identified by the specified ISO 4217 numeric currency code `{n}`. Example:
  `GET /currencies/840`
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
