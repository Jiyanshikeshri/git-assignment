// starting with student data (given in assignment)
// also added extra students to test fail conditions
const students = [
  {
    name: "Lalit",
    marks: [
      { subject: "Math", score: 78 },
      { subject: "English", score: 82 },
      { subject: "Science", score: 74 },
      { subject: "History", score: 69 },
      { subject: "Computer", score: 88 }
    ],
    attendance: 82
  },
  {
    name: "Rahul",
    marks: [
      { subject: "Math", score: 90 },
      { subject: "English", score: 85 },
      { subject: "Science", score: 80 },
      { subject: "History", score: 76 },
      { subject: "Computer", score: 92 }
    ],
    attendance: 91
  },
  {
    name: "Riya",
    marks: [
      { subject: "Math", score: 55 },
      { subject: "English", score: 60 },
      { subject: "Science", score: 58 },
      { subject: "History", score: 52 },
      { subject: "Computer", score: 50 }
    ],
    attendance: 70 // below 75 → fail case
  },
  {
    name: "Latika",
    marks: [
      { subject: "Math", score: 72 },
      { subject: "English", score: 68 },
      { subject: "Science", score: 38 }, // <=40 → fail case
      { subject: "History", score: 65 },
      { subject: "Computer", score: 70 }
    ],
    attendance: 85
  }
];

console.log("Student data loaded:", students);

// calculating total marks for a student by adding all subject scores
function getTotalMarks(students) {
  let total = 0;
  students.marks.forEach(function(mark) {
    total += mark.score;
  });

  return total;
}

// testing the function for students
console.log("Lalit Total Marks:", getTotalMarks(students[0]));
console.log("Rahul Total Marks:", getTotalMarks(students[1]));
console.log("Riya Total Marks:", getTotalMarks(students[2]));
console.log("Latika Total Marks:", getTotalMarks(students[3]));

// calculating average marks using total marks
function averageMarks(student) {
  const total = getTotalMarks(student); // reusing previous function
  const avg = total / student.marks.length;
  return avg;
}

// Average marks for each student:- 
console.log("\n Average marks for each student : ");
for(let i = 0; i < students.length; i++){
    const average = averageMarks(students[i]);
    console.log(`${students[i].name} Average : ${average.toFixed(2)}`);
}

// deciding grade based on average + fail conditions
function gradeForStudents(students) {
  const avg = averageMarks(students);

  // fail if attendance is less than 75
  if (students.attendance < 75) {
    return "Fail due to low attendance";
  }

  // fail if any subject has marks <= 40
  for (let i = 0; i < students.marks.length; i++) {
    if (students.marks[i].score <= 40) {
      return "Fail (Failed in " + students.marks[i].subject + ")";
    }
  }

  // grade based on average
  if (avg >= 85) return "A";
  if (avg >= 70) return "B";
  if (avg >= 50) return "C";

  return "Fail";
}

console.log("\nStudent Grades:");
for (let i = 0; i < students.length; i++) {
  console.log(students[i].name + " Grade: " + gradeForStudents(students[i]));
}

// finding highest score for a given subject by checking all students
// list of all the subjects
let subjects = ["Math", "English", "Science", "History", "Computer"];

subjects.forEach(function(sub) {
  let highestScore = 0;
  let topperName = "";

  students.forEach(function(student) {
    student.marks.forEach(function(mark) {

      // check if subject is matching and score is highest
      if (mark.subject === sub && mark.score > highestScore) {
        highestScore = mark.score;
        topperName = student.name;
      }

    });
  });

  console.log("Highest in " + sub + ": " + topperName + " = " + highestScore);
});

// Calculating Subject-wise Average Score (list of subjects has already been declared)
subjects.forEach(function(sub) {
  let total = 0;

  students.forEach(function(student) {
    student.marks.forEach(function(mark) {

      // checking if subject matches
      if (mark.subject === sub) {
        total += mark.score;
      }

    });
  });

  // calculating average
  let avg = total / students.length;

  console.log("Average " + sub + " Score: " + avg);
});