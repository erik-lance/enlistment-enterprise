package com.orangeandbronze.enlistment;

class Student {
	private final int studentNumber;

	Student(int studentNumber) {
		if (studentNumber < 0) {
			throw new IllegalArgumentException("studentNumber must be non-negative, was: " + studentNumber);
		}
		this.studentNumber = studentNumber;
	}
	
	@Override
	public String toString() {
		return "Student #" + studentNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + studentNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (studentNumber != other.studentNumber)
			return false;
		return true;
	}

}
