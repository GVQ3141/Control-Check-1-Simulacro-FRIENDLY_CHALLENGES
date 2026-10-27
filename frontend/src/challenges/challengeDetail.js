export default function ChallengeDetail({ ranking }) {
    return (
        <div>
            <ul>
                {ranking.map((entry) => (
                    <li key={entry.user.id}>{entry.user.username} - {entry.points}</li>
                ))}
            </ul>
        </div>
    );
}
