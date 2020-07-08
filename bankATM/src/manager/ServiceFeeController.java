package manager;

import bankATM.Money;

public interface ServiceFeeController {

	public abstract void setWithdrawServiceFee(Money fee);

	public abstract void setTranserServiceFee(Money fee);

	public abstract void setDepositServiceFee(Money fee);

	public abstract void setCloseAccountFee(Money fee);

	public abstract void setOpenAccountFee(Money fee);

}
