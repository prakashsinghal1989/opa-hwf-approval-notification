/**
 * CheckBox Component
 */
import clsx from 'clsx';
import { string } from 'prop-types';
import { useField, Field } from 'formik';

// Components
import Label from '../Label';

// Styles
import './styles.scss';

function CheckBox({ additonalClasses, id, text, ...props }) {
	const [field] = useField(props);
	const { value } = field;
	return (
		<div className={clsx('opa-checkbox-component d-flex align-items-center', additonalClasses)}>
			<Label htmlFor={id} additonalClasses="d-flex align-items-center">
				<div className="opa-checkbox-component__input-container">
					<Field type="checkbox" className="opa-checkbox-component__input" {...props} />
					{value && <i className="fas fa-check opa-checkbox-component--icon" />}
				</div>
				{text}
			</Label>
		</div>
	);
}

CheckBox.propTypes = {
	additonalClasses: string,
	id: string.isRequired,
	text: string,
};

CheckBox.defaultProps = {
	additonalClasses: '',
	text: '',
};

export default CheckBox;
