export const getCheckInTimeFromUtilities = (rules) => {
  const checkIn = rules.descriptions.find(rule => rule.description.toLowerCase().includes("check-in"))
  return {
    checkIn
  }
}