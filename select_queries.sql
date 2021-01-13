USE PersonalTrainer;

SELECT *
FROM ExerciseCategory
INNER JOIN Exercise
ON Exercise.ExerciseCategoryId = ExerciseCategory.ExerciseCategoryId;

SELECT *
FROM Client;

SELECT *
FROM Client
WHERE City = 'Metairie';

SELECT *
FROM Client
WHERE ClientId = '818u7faf-7b4b-48a2-bf12-7a26c92de20c';

SELECT *
FROM Goal;

SELECT name,
		levelid
FROM Workout;

SELECT name,
		levelId,
        notes
FROM Workout
WHERE levelId = 2;

SELECT firstname,
		lastname,
        city
FROM Client
WHERE city = 'Metairie'
OR city = 'Kenner'
OR city = 'Gretna';

SELECT firstname,
		lastname,
        birthdate
FROM Client
WHERE birthdate BETWEEN '1980-01-01' AND '1989-12-31';

SELECT firstname,
		lastname,
        birthdate
FROM Client
WHERE birthdate >= '1980-01-01' AND birthdate <= '1989-12-31';

SELECT ClientId,
		EmailAddress,
        PasswordHash
FROM Login
WHERE EmailAddress LIKE '%.gov';

SELECT ClientId,
		EmailAddress,
        PasswordHash
FROM Login
WHERE EmailAddress NOT LIKE '%.com';

SELECT firstname,
		lastname
FROM Client
WHERE BirthDate IS NULL;

SELECT name
FROM ExerciseCategory
WHERE ParentCategoryId IS NOT NULL;

SELECT name,
		notes
FROM Workout
WHERE LevelId = 3 AND notes LIKE '%you%';

SELECT firstname,
		lastname,
        city
FROM Client
WHERE city = 'LaPlace'
AND (lastname LIKE 'L%' OR lastname LIKE 'M%' OR lastname LIKE 'N%');

SELECT invoiceid,
		description,
        price,
        quantity,
        price * quantity AS line_item_total
FROM InvoiceLineItem
WHERE price * quantity BETWEEN 15 AND 25;

SELECT EmailAddress
FROM Login
WHERE ClientId = (
	SELECT ClientId
	FROM Client
	WHERE FirstName = 'Estrella'
	AND LastName = 'Bazely');

SELECT Name
FROM Goal
WHERE GoalId IN (
	SELECT GoalId
	FROM WorkoutGoal
	WHERE WorkoutId = (
		SELECT WorkoutId
		FROM Workout
		WHERE Name = 'This Is Parkour'));