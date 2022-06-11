- H2 memory database does not work due to file permission issue
 
- Start `postgres` container using `run-local-postgres.sh` in the project directory

- Install postgres client: `sudo apt install postgresql-client`

- Access the database: `psql -h localhost -p 5432 -U sample -d spring`

- Install tomcat9 on Ubuntu:
    - `sudo apt install tomcat9 tomcat9-admin`
    - Add admin user by editing `/etc/tomcat9/tomcat-users.xml`

      ```xml
      <tomcat-users>
                                        
        ...
      
        <role rolename="admin-gui"/>
      
        <role rolename="manager-gui"/>
      
        <user username="tomcat" password="pass" roles="admin-gui,manager-gui"/>
      </tomcat-users>
      ```
    - Go to `http://127.0.0.1:8080/manager/html`

- In `chapter06` directory, run:
  - `../gradlew clean build -x test`
  - `sudo cp mvc-basic-practice/build/libs/mvc-basic-practice-1.2-SNAPSHOT.war /var/lib/tomcat9/webapps/mvc-basic-practice.war`
  - `sudo systemctl restart tomcat9`
  
- Track log
  - `tail -f /var/lib/tomcat9/logs/localhost.<date>.log`
  - `journalctl -f -u tomcat9.service`