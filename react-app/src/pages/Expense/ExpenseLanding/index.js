// Components
import GridCell from '../../../components/GridCell';
// Styles
import './styles.scss';

const Expense = () => {
	return (
		<div className="opa-expense-landing">
			<div className="opa-expense-landing--bg" />
			<div className="opa-expense-landing__container">
				<h2 className="opa-expense-landing--title">Expense Reports</h2>
				<div className="opa-expense-landing__reports">
					<GridCell
						iconName="plus"
						appText="Create Report"
						routeUrl="/expense-landing/create-report"
						additionalClasses="opa-expense-landing__reports--create-report"
					/>
				</div>
			</div>
		</div>
	);
};

export default Expense;
