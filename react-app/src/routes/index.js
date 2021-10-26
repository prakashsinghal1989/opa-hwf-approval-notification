import { Suspense, lazy } from 'react';
import { Switch, Route } from 'react-router-dom';

// import all the routes
const Home = lazy(() => import('../pages/Home'));
const ExpenseLanding = lazy(() => import('../pages/Expense/ExpenseLanding'));
const CreateReport = lazy(() => import('../pages/Expense/CreateReport'));
const Routes = () => {
	return (
		<Suspense fallback={<div>Loading ...</div>}>
			<Switch>
				<Route path="/expense-landing" exact>
					<ExpenseLanding />
				</Route>
				<Route path="/expense-landing/create-report" exact>
					<CreateReport />
				</Route>
				<Route path="/">
					<Home />
				</Route>
			</Switch>
		</Suspense>
	);
};

export default Routes;
