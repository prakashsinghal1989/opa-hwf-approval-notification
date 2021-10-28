/**
 * Dropdown Component
 */
import { string, bool, arrayOf, shape } from 'prop-types';
import clsx from 'clsx';
import { useField, Field } from 'formik';

// Components
import Label from '../Label';

// Styles
import './styles.scss';

function Dropdown({
	additionalClasses,
	label,
	dropDownId,
	dropDownItems,
	disabled,
	placeholderText,
	...props
}) {
	const [field, meta] = useField(props);
	const { value: selectedvalue = '' } = field;

	const { touched, error } = meta;

	const isInValid = touched && error;
	return (
		<div className="opa-dropdown">
			<div className={clsx(`opa-dropdown__container ${additionalClasses}`)}>
				<Field
					as="select"
					name={dropDownId}
					className={clsx('opa-dropdown__box', {
						invalid: isInValid,
						disabled,
					})}
					value={selectedvalue}
					aria-label={dropDownId}
					disabled={disabled}
					{...props}
				>
					{placeholderText && <option value="">{placeholderText}</option>}
					{dropDownItems.map((item) => {
						const { name, value } = item;
						return (
							<option value={value} key={`option-${name}`}>
								{name}
							</option>
						);
					})}
				</Field>

				<i className="fas fa-angle-down opa-dropdown__icon" />

				{label && (
					<Label htmlFor={dropDownId} additonalClasses="opa-dropdown__label" size="medium">
						{label}
					</Label>
				)}
			</div>
			{isInValid && <p className="opa-dropdown__error-msg">{error}</p>}
		</div>
	);
}

const dropDownItem = {
	name: string,
	value: string,
};

Dropdown.propTypes = {
	additionalClasses: string,
	label: string,
	dropDownId: string.isRequired,
	dropDownItems: arrayOf(shape(dropDownItem)).isRequired,
	disabled: bool,
	placeholderText: string,
};

Dropdown.defaultProps = {
	additionalClasses: '',
	label: '',
	disabled: false,
	placeholderText: '',
};

export default Dropdown;
