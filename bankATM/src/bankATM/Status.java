package bankATM;

public enum Status {
	
	//Transaction Status
	Pending("Pending"), 
	Completed("Completed"),
	Cancelled("Cancelled"),
	//Loan Request/Approve Status
	Requested("Requested"),
	Approved("Approved"),
	Rejected("Rejected"),
	//Stock Status
	Sold("Sold"), 
	Purchased("Purchased"), 
	//SoldStock Status
	Realized("Realized"), 
	Unrealized("Unrealized"),
	//Account Status, Loan Status
	Closed("Closed"),
	Open("Open");

	public String str;

	Status(String str) {
		this.str = str;
	}

	public boolean equals(Status other) {
		if (this == other) {
			return true;
		}
		return false;
	}

	public boolean equals(String other) {
		if (str.toString().equals(other)) {
			return true;
		}
		return false;
	}

	public String toString() {
		return str;
	}

}
