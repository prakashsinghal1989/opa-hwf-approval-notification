/**
 * Page Layout Component which renders the entire page  with header,left navigation menu and the main Content
 */
import { string, node } from 'prop-types';
import { Link } from 'react-router-dom';
import { useContext } from 'react';

// Context
import { AppContext } from '../../contexts/AppContext';

// Components
import Loader from '../Loader';
// Logo Assets
import LogoImage from '../../assets/images/logo.png';

// Styles
import './styles.scss';

const PageLayout = ({ additonalClasses, children }) => {
	const [loaderVisible] = useContext(AppContext);
	return (
		<div className={`opa-page-layout ${additonalClasses}`}>
			<header className="opa-page-layout__header d-flex align-items-center">
				<Link
					to={{
						pathname: '/',
					}}
					className="d-flex flex-column justify-content-center align-items-center opa-grid-cell--link "
				>
					<img
						className="opa-page-layout__header--logo"
						src={LogoImage}
						alt="Logo"
						data-testid="logo-img"
					/>
				</Link>
			</header>
			<main className="opa-page-layout__content">{children}</main>
			{loaderVisible && <Loader />}
		</div>
	);
};

PageLayout.propTypes = {
	additonalClasses: string,
	children: node,
};

PageLayout.defaultProps = {
	additonalClasses: '',
	children: null,
};

export default PageLayout;
