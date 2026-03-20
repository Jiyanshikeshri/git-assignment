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