/**
 * Generic Loader Component
 */

// styles
import './styles.scss';

const Loader = () => {
	return (
		<div className="opa-loader opa-loader--wrapper">
			<div className="opa-loader--container" />
		</div>
	);
};

Loader.propTypes = {};

Loader.defaultProps = {};

export default Loader;
