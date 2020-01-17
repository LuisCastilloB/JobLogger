# JobLogger
It allows to process certain type of log to a specific destination

  - To A log File
  - To A log Console
  - To A record in a DB Table

# New Features!

  - Refactor of the Code aplying POO and good practices
  - Junit Test is added

### Installation

First create the table (log_values) in the Mysql DB (logs)

```sql
CREATE TABLE `log_values` (
  `content` text NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

Next, configure the correct credentials to autenticate in the DataBase find this in the file: 

db.properties