import { InputHTMLAttributes } from 'react'

type SignInInputProps = InputHTMLAttributes<HTMLInputElement> & {
    label: string;
}

const SignInInput: React.FC<SignInInputProps> = ({ label, ...otherProps }) => {
  return (
    <div className='relative mt-6 mb-6'>
        <input
        className="peer block w-full px-4 pt-6 pb-1 text-md border border-darkGray rounded-md appearance-none focus:outline-none focus:ring-1 focus:ring-primary focus:border-primary"
        placeholder=" "
        {...otherProps}
      />
      <label
        className="absolute text-darkGray font-light text-md duration-200 transform -translate-y-3 scale-75 top-4 z-10 origin-[0] left-4 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-3 peer-placeholder-shown:top-5"
      >
        {label}
      </label>
    </div>
  )
}

export default SignInInput