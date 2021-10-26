/**
 * Generic GridCell Component
 */
import { string } from 'prop-types';
import { Link } from 'react-router-dom';

// Styles
import './styles.scss';

function GridCell({ iconName, additionalClasses, appText, routeUrl }) {
	return (
		<div
			className={`opa-grid-cell d-flex flex-column justify-content-center align-items-center ${additionalClasses}`}
		>
			<Link
				to={{
					pathname: routeUrl,
				}}
				className="d-flex flex-column justify-content-center align-items-center w-100 h-100 opa-grid-cell--link "
			>
				<i className={`fas fa-${iconName}`} />
				{appText}
			</Link>
		</div>
	);
}

GridCell.propTypes = {
	iconName: string,
	additionalClasses: string,
	appText: string.isRequired,
	routeUrl: string.isRequired,
};

GridCell.defaultProps = {
	iconName: '',
	additionalClasses: '',
};

export default GridCell;
