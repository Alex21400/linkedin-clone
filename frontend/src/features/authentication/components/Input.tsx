import { InputHTMLAttributes } from 'react'

type InputProps = InputHTMLAttributes<HTMLInputElement> & {
    label: string;
}

const Input: React.FC<InputProps> = ({ label, ...otherProps }) => {
  return (
    <div className='grid gap-2 mb-4 mt-4'>
        <label className='text-sm font-medium'>{label}</label>
        <input className={`p-3 w-full border border-[(0,0,0,0.6)] rounded`} {...otherProps} />
    </div>
  )
}

export default Input