/**
 * Page Layout Component which renders the entire page  with header,left navigation menu and the main Content
 */
import { string, node } from 'prop-types';
import { Link } from 'react-router-dom';

// Logo Assets
import LogoImage from '../../assets/images/logo.png';

// Styles
import './styles.scss';

const PageLayout = ({ additonalClasses, children }) => {
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
