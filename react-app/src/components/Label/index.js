/**
 * Label Component
 */
import clsx from 'clsx';
import { node, string, oneOf } from 'prop-types';
import './styles.scss';

function Label({ additonalClasses, htmlFor, children, size, ...props }) {
	return (
		<label
			className={clsx(additonalClasses, 'opa-label-component', size)}
			data-testid="label"
			htmlFor={htmlFor}
			{...props}
		>
			{children}
		</label>
	);
}

Label.propTypes = {
	children: node.isRequired,
	htmlFor: string,
	additonalClasses: string,
	size: oneOf(['medium', 'large', 'extra-large', 'small', 'extra-small']),
};

Label.defaultProps = {
	additonalClasses: '',
	htmlFor: '',
	size: 'small',
};

export default Label;
