/**
 * TextArea Component
 */
import clsx from 'clsx';
import { string, bool, oneOf } from 'prop-types';
import { useRef } from 'react';
import { useField } from 'formik';

// Components
import Label from '../Label';
// Styles
import './styles.scss';

const TextArea = ({ additionalClasses, id, type, readOnly, ariaLabel, labelKey, ...props }) => {
	const textArea = useRef(null); //  reference to the input field
	const [field, meta] = useField(props);
	const { value = '' } = field;
	const { touched, error } = meta;
	const isInValid = touched && error;

	return (
		<div className={clsx(additionalClasses, 'opa-textarea')}>
			<div className={clsx(additionalClasses, 'opa-textarea__container')}>
				<textarea
					className={clsx('opa-textarea__input', {
						disabled: readOnly,
						'has-value': value && value.toString().length > 0,
						error: isInValid,
					})}
					ref={textArea}
					{...field}
					{...props}
				/>
				<Label htmlFor={id} additonalClasses="opa-textarea__label" size="medium">
					{labelKey}
				</Label>
			</div>
			{isInValid && <p className="opa-error-text">{error}</p>}
		</div>
	);
};

TextArea.propTypes = {
	additionalClasses: string,
	id: string.isRequired,
	readOnly: bool,
	type: oneOf(['text', 'email', 'password', 'number', 'time', 'tel', 'date']),
	ariaLabel: string,
	labelKey: string,
};

TextArea.defaultProps = {
	additionalClasses: '',
	ariaLabel: 'TextArea',
	labelKey: '',
	readOnly: false,
	type: 'text',
};

export default TextArea;
