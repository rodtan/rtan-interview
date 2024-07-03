# rtan-interview

Jira board: https://rod-interview.atlassian.net/jira/software/projects/RT/boards/1

Setting up
====
1. Clone this repository: `git clone git@github.com:rodtan/rtan-interview.git`
2. Run `cd rtan-interview` or change to the repository directory
3. Run `gradle clean build`
4. Open this up in your preferred IDE

Running
====
1. Run `gradle bootRun`
2. Check to see if swagger-ui is up: http://localhost:8080/swagger-ui/index.html
3. Test using Postman or your preferred API tool

Endpoints
====
You can also try out the endpoints locally at http://localhost:8080/swagger-ui/index.html

`POST /users` - Creates a user. Note that email has to be in a valid format, e.g. `rtan@example.com` or a 400 is returned

`DELETE /users/{id}` - Deletes a user, 404 status if not found

`PUT /users/{id}` - Updates a user, 404 status if not found

`GET /users/{id}` - Gets a single user, 404 status returned if not found

`GET /users` - Gets all users

`POST /posts` - Creates a posts

`DELETE /posts/{id}` - Deletes a posts, 404 status if not found

`PUT /posts/{id}` - Updates a posts, 404 status if not found

`GET /posts/{id}` - Gets a single posts, 404 status if not found

`GET /posts` - Gets all posts

Testing
====
For testing (CLI examples below, or run from within your IDE): 

run `gradle test`

For integration tests:

run `gradle integrationTest`

Limitations & Future Work
====
1. Set up PR checks - linting/formatting, testing
2. Set up containerization for PR merges
3. Auto-push to a cloud platform (too cheap to open an account atm)
4. Proper validation checks on some fields - only email is validated for now
5. Encrypting email in the database
6. Using an in-memory database for now so this can be run as a portable app.
7. 