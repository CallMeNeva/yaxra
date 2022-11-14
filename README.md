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
