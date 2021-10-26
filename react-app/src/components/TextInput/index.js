import clsx from 'clsx';
import { string, bool, oneOf } from 'prop-types';
import { useRef } from 'react';
import { useField } from 'formik';

// Components
import Label from '../Label';
// Styles
import './styles.scss';

const TextInput = ({ additionalClasses, id, type, readOnly, ariaLabel, labelKey, ...props }) => {
	const textInput = useRef(null); //  reference to the input field
	const [field, meta] = useField(props);
	const { value = '' } = field;
	const { touched, error } = meta;
	const isInValid = touched && error;

	return (
		<div className={clsx(additionalClasses, 'opa-text-input')}>
			<div className={clsx(additionalClasses, 'opa-text-input__container')}>
				<input
					className={clsx('opa-text-input__input', {
						disabled: readOnly,
						'has-value': value && value.toString().length > 0,
						error: isInValid,
						'opa-text-input__input--date-type': type === 'date',
					})}
					id={id}
					ref={textInput}
					type={type}
					value={value}
					readOnly={readOnly}
					aria-label={ariaLabel}
					{...field}
					{...props}
				/>
				<Label htmlFor={id} additonalClasses="opa-text-input__label" size="medium">
					{labelKey}
				</Label>
			</div>
			{isInValid && <p className="opa-text-input--error-text ">{error}</p>}
		</div>
	);
};

TextInput.propTypes = {
	additionalClasses: string,
	id: string.isRequired,
	readOnly: bool,
	type: oneOf(['text', 'email', 'password', 'number', 'time', 'tel', 'date']),
	ariaLabel: string,
	labelKey: string,
};

TextInput.defaultProps = {
	additionalClasses: '',
	ariaLabel: 'TextInput',
	labelKey: '',
	readOnly: false,
	type: 'text',
};

export default TextInput;
