// Components
import GridCell from '../../components/GridCell';
// Styles
import './styles.scss';

const Home = () => {
	return (
		<div className="opa-home">
			<div className="opa-home__container">
				<h1 className="opa-home--title"> Good afternoon, Siddhant Wadhera!</h1>
				<h6 className="opa-home--apps-title">APPS</h6>
				<GridCell iconName="wallet" appText="Expenses" routeUrl="/expense-landing" />
			</div>
		</div>
	);
};

export default Home;
